FROM alpine:3.10.2 as builder

ARG AGENT_VERSION=5.7.0
ARG AGENT_RELEASE=https://download.newrelic.com/newrelic/java-agent/newrelic-agent/${AGENT_VERSION}

WORKDIR /agent
RUN set -ex \
    && apk add --no-cache curl \
    && curl -LO "${AGENT_RELEASE}/newrelic.yml" \
    && curl -LO "${AGENT_RELEASE}/newrelic-agent-${AGENT_VERSION}.jar"

FROM azell/java-docker:8

COPY --from=builder /agent/newrelic.yml /agent/
COPY --from=builder /agent/*.jar /agent/newrelic-agent.jar

ENV JAVA_TOOL_OPTIONS="$JAVA_TOOL_OPTIONS -javaagent:/agent/newrelic-agent.jar"
