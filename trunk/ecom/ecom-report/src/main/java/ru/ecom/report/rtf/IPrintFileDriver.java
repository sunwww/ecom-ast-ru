package ru.ecom.report.rtf;

import java.io.File;

import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.ReplaceHelper;

public interface IPrintFileDriver {

	boolean isAccept(File aDir, String aKeyName) ;
	
	/**
	 * Создать новый драйвер для сессии
	 * @param id            идентификатор сессии
	 * @param templateDir   каталог с шаблоном
	 * @param key           ключ
	 * @param theWorkDir    каталог для записи нового файла
	 * @return
	 */
	IPrintFileDriver newInstance(String id, File templateDir, String key, String theWorkDir);

	/**
	 * Кодировка
	 * @return
	 */
	String getEncoding() ;
	
	/**
	 * Поддержка RTF замены {{
	 * @return
	 */
	boolean isRtfReplaceMode() ;

	/**
	 * Подготовка файла
	 */
	void prepare();
	public void buildFile(boolean aRemovedTempFile) ;

	/**
	 * Входной файл
	 * @return
	 */
	File getInputFile();
	
	/**
	 * Выходной файл
	 * @return
	 */
	File getOutputFile() ;

	/**
	 * Создание файла
	 */
	void buildFile();

	/**
	 * Файл с результатом
	 * @return
	 */
	String getResultFilename();
	void print(ReplaceHelper aReplaceHelper, IValueGetter aValueGetter) throws RtfPrintException;


}
