// Copyright 2000-2026 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.jetbrains.java.decompiler.main.decompiler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleDecompilerTest {
  @TempDir
  private Path tempDir;

  @Test
  void getBytecodeReusesInputArchive() throws IOException {
    Path archive = tempDir.resolve("input.jar");
    byte[] bytecode = new byte[] {1, 2, 3};
    try (ZipOutputStream out = new ZipOutputStream(java.nio.file.Files.newOutputStream(archive))) {
      out.putNextEntry(new ZipEntry("pkg/Test.class"));
      out.write(bytecode);
    }

    CountingConsoleDecompiler decompiler = new CountingConsoleDecompiler(tempDir.toFile());
    try {
      assertArrayEquals(bytecode, decompiler.getBytecode(archive.toString(), "pkg/Test.class"));
      assertArrayEquals(bytecode, decompiler.getBytecode(archive.toString(), "pkg/Test.class"));
      assertEquals(1, decompiler.openArchiveCount);
    }
    finally {
      decompiler.close();
    }
  }

  private static class CountingConsoleDecompiler extends ConsoleDecompiler {
    private int openArchiveCount;

    private CountingConsoleDecompiler(File destination) {
      super(destination, Map.of(), new PrintStreamLogger(System.out));
    }

    @Override
    protected ZipFile openArchive(File file) throws IOException {
      openArchiveCount++;
      return super.openArchive(file);
    }
  }
}
