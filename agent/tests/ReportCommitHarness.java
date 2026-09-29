package io.rwagent.client;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Locale;
public final class ReportCommitHarness {
    public static void main(String[] args)throws Exception{
        Path root=Files.createTempDirectory(Paths.get("."),"rwreportcommit-");
        File target=root.resolve("test.jsonl").toFile();
        FileOutputStream output=ReportFiles.open(target);
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(output,StandardCharsets.UTF_8));
        String first="{\"event\":\"health\",\"message\":\"中文\"}\n",last="{\"event\":\"summary\",\"outcome\":\"PASS\"}\n";
        writer.write(first);writer.flush();
        Path partial=root.resolve("test.jsonl.partial");
        // Renaming a file that still has an open handle is POSIX-only: Windows refuses it with a
        // sharing violation, so this one simulation is skipped there instead of failing the suite.
        // Everything below still exercises the real commit path on both platforms.
        boolean posix=!System.getProperty("os.name","").toLowerCase(Locale.ROOT).contains("win");
        if(posix){
            // Simulate a filesystem reader materializing an older journal snapshot at the path
            // while the writer's open descriptor continues on the original inode.
            Files.move(partial,root.resolve("displaced-journal"));
            Files.write(partial,first.getBytes(StandardCharsets.UTF_8));
        }else{
            System.out.println("REPORT_COMMIT_TEST_SKIP posix-only open-handle rename simulation on "
                    +System.getProperty("os.name")+"; remaining commit checks still run");
        }
        writer.write(last);ReportFiles.finish(writer,output,target);
        if(!Arrays.equals(Files.readAllBytes(target.toPath()),(first+last).getBytes(StandardCharsets.UTF_8)))throw new AssertionError("Committed snapshot lost appended events");
        if(Files.exists(partial))throw new AssertionError("partial not cleaned");
        if(posix)Files.delete(root.resolve("displaced-journal"));
        Files.delete(target.toPath());Files.delete(root);
        System.out.println("REPORT_COMMIT_TEST_OK stale journal replacement retained complete UTF-8 content"
                +(posix?"":" (displacement simulation skipped)"));
    }
}
