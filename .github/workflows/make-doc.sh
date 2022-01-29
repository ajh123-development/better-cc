#!/usr/bin/env bash

DEST="${GITHUB_REF#refs/*/}/"
echo "Uploading docs to $SSH_HOST/$DEST"

sudo apt install -y lftp
set ssl:verify-certificate no
lftp -e "set ftp:ssl-allow no; mirror -R $GITHUB_WORKSPACE/docs-build/* $DEST; quit" -u $SSH_USER,$SSH_KEY $SSH_HOST