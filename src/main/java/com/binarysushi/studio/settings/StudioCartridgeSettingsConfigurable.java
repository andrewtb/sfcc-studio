package com.binarysushi.studio.settings;

import com.binarysushi.studio.StudioBundle;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class StudioCartridgeSettingsConfigurable implements SearchableConfigurable, Configurable.NoScroll, Disposable {
    private StudioCartridgePanel myCartridgePanel;
    private final Project myProject;

    public StudioCartridgeSettingsConfigurable(Project project) {
        myProject = project;
        myCartridgePanel = new StudioCartridgePanel(project, StudioSettingsProvider.getInstance(project));
    }

    @Override
    public void dispose() {
        myCartridgePanel = null;
    }

    @NotNull
    @Override
    public String getId() {
        return "StudioCartridgeSettingsConfigurable";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return StudioBundle.message("studio.server.cartridges.panel.title");
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return myCartridgePanel.createPanel();
    }

    @Override
    public boolean isModified() {
        return myCartridgePanel.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        StudioSettingsProvider mySettingProvider = StudioSettingsProvider.getInstance(myProject);
        mySettingProvider.setCartridgeRoots(myCartridgePanel.getListItems());
    }
}