package org.gtc.kurento.orion.subscription;

import org.kurento.commons.exception.KurentoException;

public class OrionSubscriptionException extends KurentoException {
    public OrionSubscriptionException(String message) {
        super(message);
    }
    public OrionSubscriptionException(final String msg, final Throwable throwable) {
        super(msg, throwable);
      }
}
