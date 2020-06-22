package com.virtuslab.gitmachete.frontend.ui.impl.root;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.ScrollPaneFactory;
import lombok.CustomLog;
import lombok.Getter;
import org.checkerframework.checker.guieffect.qual.UIEffect;

import com.virtuslab.gitmachete.frontend.defs.ActionGroupIds;
import com.virtuslab.gitmachete.frontend.defs.ActionPlaces;
import com.virtuslab.gitmachete.frontend.ui.api.table.BaseGraphTable;
import com.virtuslab.gitmachete.frontend.ui.providerservice.GraphTableProvider;
import com.virtuslab.gitmachete.frontend.ui.providerservice.SelectedGitRepositoryProvider;

@CustomLog
public final class GitMachetePanel extends SimpleToolWindowPanel {

  @Getter
  private final BaseGraphTable graphTable;

  @UIEffect
  public GitMachetePanel(Project project) {
    super(/* vertical */ false, /* borderless */ true);
    LOG.debug("Instantiating");

    var selectionComponent = project.getService(SelectedGitRepositoryProvider.class).getSelectionComponent();
    this.graphTable = project.getService(GraphTableProvider.class).getGraphTable();
    graphTable.queueRepositoryUpdateAndModelRefresh();

    // This class is final, so the instance is `@Initialized` at this point.

    setToolbar(createGitMacheteVerticalToolbar().getComponent());
    add(createShrinkingWrapper(selectionComponent), BorderLayout.NORTH);
    setContent(ScrollPaneFactory.createScrollPane(graphTable));
  }

  @UIEffect
  private ActionToolbar createGitMacheteVerticalToolbar() {
    var actionManager = ActionManager.getInstance();
    var toolbarActionGroup = (ActionGroup) actionManager.getAction(ActionGroupIds.ACTION_GROUP_TOOLBAR);
    var toolbar = actionManager.createActionToolbar(ActionPlaces.ACTION_PLACE_TOOLBAR, toolbarActionGroup,
        /* horizontal */ false);
    toolbar.setTargetComponent(graphTable);
    return toolbar;
  }

  @UIEffect
  static JComponent createShrinkingWrapper(JComponent component) {
    final JPanel wrapper = new JPanel(new BorderLayout());
    wrapper.add(component, BorderLayout.WEST);
    wrapper.add(Box.createHorizontalGlue(), BorderLayout.CENTER);
    return wrapper;
  }
}
