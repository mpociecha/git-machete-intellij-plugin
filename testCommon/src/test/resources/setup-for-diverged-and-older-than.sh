#!/usr/bin/env bash

set -e -o pipefail -u -x

self_dir=$(cd "$(dirname "$0")" &>/dev/null; pwd -P)
source "$self_dir"/common.sh


create_repo machete-sandbox-remote --bare

create_repo machete-sandbox
cd machete-sandbox
  git remote add origin ../machete-sandbox-remote

  create_branch root
    commit Root
  create_branch develop
    commit Develop commit
  create_branch allow-ownership-link
    commit Allow ownership links
    push
  create_branch build-chain
    commit Build arbitrarily long chains
  git checkout allow-ownership-link
    commit 1st round of fixes
  git checkout develop
    commit Other develop commit
    push
  create_branch call-ws
    commit Call web service
    commit 1st round of fixes
    push
  git checkout call-ws
    commit 2nd round of fixes
  git checkout develop
    # call-ws is merged to develop, and would have no child branches in the discovered layout,
    # so it should be skipped from the discovered layout
    git merge --ff-only call-ws

  git checkout root
  create_branch master
    commit Master commit
    push
  create_branch hotfix/add-trigger
    commit HOTFIX Add the trigger
    push
    git commit --amend -m 'HOTFIX Add the trigger (amended)'

  git branch -d root

  machete_file='
  develop
      allow-ownership-link PR #123
          build-chain
      call-ws PR #124
  master
      hotfix/add-trigger
  '
  sed 's/^  //' <<< "$machete_file" > .git/machete
cd -


create_repo machete-sandbox2
cd machete-sandbox2
  git remote add origin ../machete-sandbox-remote
  git fetch

  git checkout allow-ownership-link
  commit Newer commit
  push

  git checkout master
  commit Master newer commit
  push
cd -


cd machete-sandbox
  git fetch
cd -
