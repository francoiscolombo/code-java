FROM openjdk:8-jdk-alpine

LABEL maintainer="francois_colombo@yahoo.fr" ImageName="fc/samples/java/restapi" Version="latest"

ARG jar_path=./build/libs/user-manager-1.0-SNAPSHOT.jar
ARG user=admin
ARG group=admin
ARG uid=1000
ARG gid=1000

ENV USER_HOME /var/admin_home

RUN apk add --no-cache curl bash vim coreutils tini

# create admin user
RUN set -x \
	&& addgroup -g ${gid} -S ${group} \
	&& adduser -u ${uid} -D -S -G ${group} -h ${USER_HOME} -s /bin/bash ${user}

# copy jar
COPY ${jar_path} ${USER_HOME}/user-manager.jar

# copy startup script
COPY start.sh /usr/local/bin/start.sh
RUN chmod +x /usr/local/bin/start.sh \
    && chown ${user}:${group} /usr/local/bin/start.sh

# expose REST api port
EXPOSE 8080

# go to the user workdir
WORKDIR ${USER_HOME}

# run with the user, not root
USER ${user}

# entry point is the rest api
ENTRYPOINT ["/sbin/tini", "--", "/usr/local/bin/start.sh"]






