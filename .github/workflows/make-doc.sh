#!/usr/bin/env bash

DEST="${GITHUB_REF#refs/*/}/docs"
echo "Uploading docs to $SSH_HOST/$DEST"

sudo apt install -y lftp
# Setup ssh key
# mkdir -p "$HOME/.ssh/"
# echo "$SSH_KEY" > "$HOME/.ssh/key"
# chmod 600 "$HOME/.ssh/key"

# And upload
# rsync -avc -e "ssh -i $HOME/.ssh/key -o StrictHostKeyChecking=no -p $SSH_PORT" \
#       "$GITHUB_WORKSPACE/build/docs/lua/" \
#       "$SSH_USER@$SSH_HOST:/$DEST"
lftp -e "mirror -R $GITHUB_WORKSPACE/../build/docs/lua $DEST -u $SSH_USER,$SSH_KEY $SSH_HOST"
#rsync -avc -e "ssh -i $HOME/.ssh/key -o StrictHostKeyChecking=no -p $SSH_PORT" \
#      "$GITHUB_WORKSPACE/build/docs/javadoc/" \
#      "$SSH_USER@$SSH_HOST:/$DEST/javadoc"
