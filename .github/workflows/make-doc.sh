#!/usr/bin/env bash

DEST="${GITHUB_REF#refs/*/}/docs"
echo "Uploading docs to $SSH_HOST/$DEST"

sudo apt install -y lftp
set ssl:verify-certificate no
lftp -e "mirror -R $GITHUB_WORKSPACE/docs-build $DEST" -u $SSH_USER,$SSH_KEY $SSH_HOST