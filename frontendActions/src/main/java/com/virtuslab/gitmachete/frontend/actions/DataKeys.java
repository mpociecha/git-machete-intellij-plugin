package com.virtuslab.gitmachete.frontend.actions;

import com.intellij.dvcs.repo.Repository;
import com.intellij.openapi.actionSystem.DataKey;

import com.virtuslab.gitmachete.backend.api.IGitMacheteRepository;

public final class DataKeys {
  private DataKeys() {}

  public static final DataKey<Boolean> KEY_IS_GIT_MACHETE_REPOSITORY_READY = DataKey
      .create("IS_GIT_MACHETE_REPOSITORY_READY");
  public static final DataKey<IGitMacheteRepository> KEY_GIT_MACHETE_REPOSITORY = DataKey
      .create("GIT_MACHETE_REPOSITORY");
  public static final DataKey<String> KEY_SELECTED_BRANCH_NAME = DataKey.create("SELECTED_BRANCH_NAME");
  public static final DataKey<Repository> KEY_SELECTED_CVS_REPOSITORY = DataKey.create("SELECTED_CVS_REPOSITORY");
}
