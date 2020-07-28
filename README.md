# Git Machete IntelliJ Plugin

[![CircleCI](https://circleci.com/gh/VirtusLab/git-machete-intellij-plugin/tree/master.svg?style=shield)](https://circleci.com/gh/VirtusLab/git-machete-intellij-plugin/tree/master)
[![JetBrains Plugins](https://img.shields.io/jetbrains/plugin/v/14221-git-machete.svg)](https://plugins.jetbrains.com/plugin/14221-git-machete)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/14221-git-machete.svg)](https://plugins.jetbrains.com/plugin/14221-git-machete)

![](src/main/resources/META-INF/pluginIcon.svg)

This is a port of [git-machete](https://github.com/VirtusLab/git-machete#git-machete) into an IntelliJ plugin.

This plugin is available on [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/14221-git-machete).

![](docs/sample-workflow.gif)


## How does it work

Git Machete IntelliJ Plugin is a port of a great console tool - [git-machete](https://github.com/VirtusLab/git-machete#git-machete) - into an IntelliJ plugin.
Git Machete can help you manage yours repository branches and keep it in sync with each other and its counterparts on remote repository.
Let's see how this plugin can help you:

### Where to find this plugin

Git Machete IntelliJ Plugin is available under `Git` section (in the bottom of the IDE) in the `Git Machete` tab.
You can also use `Ctrl` + `Alt` + `Shift` + `M` shortcut to open this plugin.

![](docs/open_git_machete.gif)

### Machete file editor

If you are familiar with console equivalent of this tool ([git-machete](https://github.com/VirtusLab/git-machete#git-machete)) then for sure you know what is the `machete` file.
For those who don't know this yet, `machete` file is located inside `.git` directory in your repo.
It describes relation between branches in your repository (these relations are probably determined by order of branch creation - which branch form which - but this is not a rule).
For more information look [here](https://medium.com/virtuslab/make-your-way-through-the-git-rebase-jungle-with-git-machete-e2ed4dbacd02). <br/>

![](docs/machete_file_editor.gif)

### Rebase - the heart of Git-Machete

When branch tree is created, Git-Machete indicate relation between parent branch and each of its child branches.
If edge between them is green that mean child branch is in-sync with its parent branch - in other words there are no commits in parent branch that doesn't belong to the child.
But if there are some of that commits, then edge is red - we need to rebase child branch onto the parent.
With this plugin it's easy!
Just right click on the child branch and from context menu select `(Checkout and) Rebase Branch onto Parent`.
Standard IntelliJ rebase dialog will appear, and we can easily rebase child branch.

![](docs/rebase.gif)

### Push

After rebase it's good practice to push rebased branch to remote.
To do this right click on the branch you want to push and select `Push (Current) Branch` from context menu.
Standard IntelliJ push dialog will appear but in case when we rebased selected branch (when branch we want to push diverge from its remote) only force push button will be available (in other cases only standard push).

![](docs/push.gif)

## Build

### Prerequisites

* git
* IntelliJ 2020.1 Community Edition/Ultimate

  * Install [Lombok plugin](https://plugins.jetbrains.com/plugin/6317-lombok/)
  * Enable annotation processing (for Lombok):
    `File` -> `Settings` -> `Build`, `Execution`, `Deployment` -> `Compiler` -> `Annotation Processors` -> `Enable Annotation Processing`
  * Set Project SDK to JDK 11: `Project Structure` -> `Project`

Consider increasing maximum heap size for the IDE (the default value is 2048 MB) under `Help` -> `Change Memory Settings`.

For running `./gradlew` from command line, make sure that `java` and `javac` are in `PATH` and point to Java 11.

### Building

To build the project, run `./gradlew build`.

Currently, very generous maximum heap size options are applied for Gradle's Java compilation tasks (search for `-Xmx` in [build.gradle](build.gradle)). <br/>
To overwrite them, use `compileJavaJvmArgs` Gradle project property
(e.g. `./gradlew -PcompileJavaJvmArgs='-Xmx2g -XX:+HeapDumpOnOutOfMemoryError' build`,
or equivalently with an env var: `ORG_GRADLE_PROJECT_compileJavaJvmArgs='-Xmx2g -XX:+HeapDumpOnOutOfMemoryError' ./gradlew compileJava`).

By default, Lombok's annotation processor runs on the fly and Delomboked sources are not saved to {subproject}/build/delombok/...<br/>
To enable Delombok, set `useDelombok` Gradle project property (e.g. `./gradlew -PuseDelombok build`).

In case of spurious cache-related issues with Gradle build, try one of the following:
* `./gradlew clean` and re-run the failing `./gradlew` command with `--no-build-cache`
* remove .gradle/ directory in the project directory
* remove ~/.gradle/caches/ (or even the entire ~/.gradle/) directory


### Run & debug

To run an instance of IDE with Git Machete IntelliJ Plugin installed from the current source,
execute `:runIde` Gradle task (`Gradle panel` -> `Tasks` -> `intellij` -> `runIde` or `./gradlew runIde`).

To watch the logs of this IntelliJ instance, run `tail -F build/idea-sandbox/system/log/idea.log`.

### Generate plugin zip

To generate a plugin archive run `:buildPlugin` Gradle task (`Gradle panel` -> `Tasks` -> `intellij` -> `buildPlugin` or `./gradlew buildPlugin`).<br/>
The resulting file will be available under `build/distributions/`.


## Installing

### Install from JetBrains Marketplace

This plugin is available on [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/14221-git-machete). <br/>
To install this plugin go to `File` -> `Settings` -> `Plugins`, then make sure you are on `Marketplace` tab (not `Installed`), in search box type `Git Machete` and click install.
Plugin will be installed. After installation click `Restart IDE` and confirm that action in a messagebox.

### Install plugin zip

Go to `File` -> `Settings` -> `Plugins` -> `(gear icon)` -> `Install Plugin from Disk...`, select the zip and restart IDE.


## Logging

SLF4J logging in this plugin has the following categories:

```
binding
branchlayout
gitcore
gitmachete.backend
gitmachete.frontend.actions
gitmachete.frontend.externalsystem
gitmachete.frontend.graph
gitmachete.frontend.ui
```

The standard practice in Java logging is to use fully-qualified name of each class as category;
in our case, however, we're only ever using the categories provided above.
FQCN (and method name), however, is always a part of the log message itself.

By default, IntelliJ logs everything with level `INFO` and above into `idea.log` file. <br/>
The exact location depends on a specific IntelliJ installation; check `Help` -> `Show Log in Files` to find out. <br/>
Tip: use `tail -f` to watch the log file as it grows.

To enable logging in `DEBUG` level, add selected categories to list in `Help` -> `Diagnostic Tools` -> `Debug Log Settings`. <br/>
A relatively small amount of `TRACE`-level logs is generated as well.


## Development

For more details on development the project, see the [this document](DEVELOPMENT.md).
