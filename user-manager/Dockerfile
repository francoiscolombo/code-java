FROM openjdk:8-jdk-alpine

LABEL maintainer="francois_colombo@yahoo.fr" ImageName="fc/samples/java/restapi" Version="latest"

ARG jar_path=./build/libs/user-manager-1.0-SNAPSHOT.jar
ARG user=admin
ARG group=admin
ARG uid=1000
ARG gid=1000

# uncomment these lines if you are building your image behind a proxy
#ARG proxy
#ENV http_proxy="$proxy" \
#    https_proxy="$proxy" \
#    HTTP_PROXY="$proxy" \
#    HTTPS_PROXY="$proxy" \
#    HTTP_PROXY_REQUEST_FULLURI=1

ENV USER_HOME /var/admin_home

# say hello to java & jetty
ENV MEMORY="64m" \
    CPUS=1 \
    ACCEPTORS=1 \
    SELECTORS=1\
    JETTY_MIN_THREADS=10 \
    JETTY_MAX_THREADS=40

ENV JAVA_OPTS="-Xmx$MEMORY -XX:+HeapDumpOnOutOfMemoryError \
               -XX:OnOutOfMemoryError=\"kill -9 %p\" \
               -XX:+UseSerialGC \
               -XX:+UnlockExperimentalVMOptions \
               -XX:+UseCGroupMemoryLimitForHeap \
               -Djetty.acceptor-threads=$ACCEPTORS \
               -Djetty.selector-threads=$SELECTORS \
               -Djetty.min-threads=$JETTY_MIN_THREADS \
               -Djetty.max-threads=$JETTY_MAX_THREADS"

# install some packages
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

# volume for the path supposed to contains the user database
VOLUME ["/data"]

# go to the user workdir
WORKDIR ${USER_HOME}

# run with the user, not root
USER ${user}

# entry point is the rest api
ENTRYPOINT ["/sbin/tini", "--", "/usr/local/bin/start.sh"]