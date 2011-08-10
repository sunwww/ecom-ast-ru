package ru.nuzmsh.util.voc;

/**
 * Ошибка при выборе значений
 */
@SuppressWarnings("serial")
public class VocServiceException extends Exception {
	
	public VocServiceException(String message) {
        super(message);
    }

    public VocServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}