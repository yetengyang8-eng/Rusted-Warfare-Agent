package io.rwagent.client;
import java.io.*;
import java.nio.file.*;
/** A .jsonl name denotes a closed, flushed report; interrupted runs retain .jsonl.partial. */
final class ReportFiles {
    private ReportFiles(){}
    static FileOutputStream open(File target)throws IOException {
        File partial=new File(target.getPath()+".partial");
        if(!partial.createNewFile())throw new IOException("Report already exists: "+partial);
        return new SnapshotOutput(partial);
    }
    static void finish(BufferedWriter writer,FileOutputStream stream,File target)throws IOException {
        writer.flush();stream.getFD().sync();
        if(!(stream instanceof SnapshotOutput))throw new IOException("Report stream must retain its commit snapshot");
        byte[] complete=((SnapshotOutput)stream).snapshot();
        writer.close();
        // Write to a NEW path after completion. A reader of the live append-only journal
        // must never cause the committed report to inherit a stale filesystem snapshot.
        Path commit=target.toPath().resolveSibling(target.getName()+".commit-"+java.util.UUID.randomUUID());
        try(FileOutputStream saved=new FileOutputStream(commit.toFile())){
            saved.write(complete);saved.flush();saved.getFD().sync();
        }
        try {Files.move(commit,target.toPath(),StandardCopyOption.ATOMIC_MOVE);}
        catch(AtomicMoveNotSupportedException e){Files.move(commit,target.toPath());}
        Files.deleteIfExists(new File(target.getPath()+".partial").toPath());
    }
    private static final class SnapshotOutput extends FileOutputStream {
        private final ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        private static final int LIMIT=64*1024*1024;
        SnapshotOutput(File path)throws IOException{super(path);}
        @Override public synchronized void write(byte[] b,int off,int len)throws IOException{
            if(len>LIMIT-bytes.size())throw new IOException("Report exceeds the 64 MiB commit limit");
            super.write(b,off,len);bytes.write(b,off,len);
        }
        @Override public void write(byte[] b)throws IOException{write(b,0,b.length);}
        @Override public synchronized void write(int value)throws IOException{
            if(bytes.size()>=LIMIT)throw new IOException("Report exceeds the 64 MiB commit limit");
            super.write(value);bytes.write(value);
        }
        synchronized byte[] snapshot(){return bytes.toByteArray();}
    }
}
