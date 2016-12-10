#!/bin/bash -e

if [[ "${TRAVIS_PULL_REQUEST}" == "false" && "${TRAVIS_BRANCH}" == "master" ]]; then
  echo -e "Host github.com\n\tStrictHostKeyChecking no\nIdentityFile ~/.ssh/deploy.key\n" >> ~/.ssh/config
  openssl aes-256-cbc -k "$SERVER_KEY" -in deploy_key.enc -d -a -out deploy.key
  cp deploy.key ~/.ssh/
  chmod 600 ~/.ssh/deploy.key
  git config --global user.email "6b656e6a69+unfiltered@gmail.com"
  git config --global user.name "unfiltered-bot"
  sbt ghpagesPushSite
fi
