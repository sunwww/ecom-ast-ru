package ru.ecom.ejb.services.monitor;

public class LocalAcceptedMonitor extends LocalMonitorStatus {

	public LocalAcceptedMonitor(String aText) {
		super("Запуск ...", 0) ;
		setText(aText) ;
	}
	
	public void advice(double aAdvice) {
		throw new IllegalStateException("Ошибка использования монитора. Монитор принят, но не запущен. ") ;
	}

	public void finish(String aParameters) {
		throw new IllegalStateException("Ошибка использования монитора. Монитор принят, но не запущен. ") ;
	}

	public boolean isCancelled() {
		throw new IllegalStateException("Ошибка использования монитора. Монитор принят, но не запущен. ") ;
	}

}
