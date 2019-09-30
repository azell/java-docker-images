FROM alpine:3.10.2 as builder

ARG ACCP_VERSION=1.1.1
ARG ACCP_RAW=https://raw.githubusercontent.com/corretto/amazon-corretto-crypto-provider/${ACCP_VERSION}
ARG ACCP_RELEASE=https://github.com/corretto/amazon-corretto-crypto-provider/releases/download/${ACCP_VERSION}

WORKDIR /accp
RUN set -ex \
    && apk add --no-cache curl \
    && curl -LO "${ACCP_RAW}/etc/amazon-corretto-crypto-provider.security" \
    && curl -LO "${ACCP_RELEASE}/AmazonCorrettoCryptoProvider-${ACCP_VERSION}-linux-x86_64.jar"

FROM gcr.io/distroless/java:8

COPY --from=builder /accp/*.security /accp/
COPY --from=builder /accp/*.jar /accp/AmazonCorrettoCryptoProvider-linux-x86_64.jar

ENV JAVA_TOOL_OPTIONS="-XX:+ExitOnOutOfMemoryError \
    -XX:MinRAMPercentage=80.0 \
    -XX:MaxRAMPercentage=80.0"
