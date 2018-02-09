#!/usr/bin/env bash

# if `docker run` first argument start with `--` the user is passing java launcher arguments
if [[ $# -lt 1 ]] || [[ "$1" == "--"* ]]; then

  # read JAVA_OPTS into arrays to avoid need for eval (and associated vulnerabilities)
  java_opts_array=()
  while IFS= read -r -d '' item; do
    java_opts_array+=( "$item" )
  done < <([[ $JAVA_OPTS ]] && xargs printf '%s\0' <<<"$JAVA_OPTS")

  exec java -Duser.home="$USER_HOME" "${java_opts_array[@]}" -jar ${USER_HOME}/user-manager.jar "$@"

fi

# As argument is not java, assume user want to run his own process, for example a `bash` shell to explore this image
exec "$@"