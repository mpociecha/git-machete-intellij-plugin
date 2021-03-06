package com.virtuslab.gitmachete.frontend.file;

import com.intellij.psi.PsiFile;
import git4idea.repo.GitRepositoryManager;
import io.vavr.collection.List;

import com.virtuslab.gitmachete.frontend.vfsutils.GitVfsUtils;

public final class MacheteFileUtils {
  private MacheteFileUtils() {}

  public static List<String> getBranchNamesForPsiFile(PsiFile psiFile) {
    var project = psiFile.getProject();

    var gitRepository = List.ofAll(GitRepositoryManager.getInstance(project).getRepositories())
        .find(repository -> GitVfsUtils.getMacheteFile(repository)
            .map(macheteFile -> macheteFile.equals(psiFile.getVirtualFile())).getOrElse(false));

    if (gitRepository.isEmpty()) {
      return List.empty();
    }

    return List.ofAll(gitRepository.get().getInfo().getLocalBranchesWithHashes().keySet())
        .map(localBranch -> localBranch.getName());
  }
}
