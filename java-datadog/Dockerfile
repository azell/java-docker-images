FROM alpine:3.12.0 as builder

ARG AGENT_VERSION=0.64.0
ARG AGENT_RELEASE=https://github.com/DataDog/dd-trace-java/releases/download/v${AGENT_VERSION}

WORKDIR /agent
RUN set -ex \
    && apk add --no-cache curl \
    && curl -LO "${AGENT_RELEASE}/dd-java-agent-${AGENT_VERSION}.jar"

FROM azell/java-docker:8

COPY --from=builder /agent/*.jar /agent/dd-java-agent.jar

ENV JAVA_TOOL_OPTIONS="$JAVA_TOOL_OPTIONS -javaagent:/agent/dd-java-agent.jar"
