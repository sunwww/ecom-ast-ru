package ru.ecom.clazz;

import java.lang.reflect.Method;

import ru.ecom.gwt.clazz.client.service.CommandsHolder;
import ru.ecom.gwt.clazz.client.service.ICommandService;
import ru.ecom.gwt.clazz.client.service.command.AddClassCommand;
import ru.ecom.gwt.clazz.client.service.command.AddPropertyCommand;
import ru.ecom.gwt.clazz.client.service.command.SetClassCommentCommand;
import ru.nuzmsh.util.PropertyUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ClassCommandServiceImpl extends RemoteServiceServlet implements ICommandService {

	
	public CommandsHolder loadClass(String aClassfullname) {
		CommandsHolder holder = new CommandsHolder() ;
		
		AddClassCommand ac = new AddClassCommand() ;
		ac.setClassFullname(aClassfullname);
		holder.add(ac);
		
		SetClassCommentCommand sc = new SetClassCommentCommand() ;
		sc.setClassFullname(aClassfullname);
		sc.setComment("Comment");
		holder.add(sc);
		
		
		// properties
		try {
			Class clazz = Class.forName(aClassfullname);
			Method[] methods = clazz.getMethods();
			for(Method m : methods) {
				if(m.getName().startsWith("get") || m.getName().startsWith("is")) {
					String name = PropertyUtil.getPropertyName(m);
					String type = m.getReturnType().getName();
					
					AddPropertyCommand apc = new AddPropertyCommand() ;
					apc.setClassFullname(aClassfullname);
					apc.setPropertyName(name);
					apc.setType(type);
					holder.add(apc);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
		
		return holder;
	}

	public CommandsHolder loadClasses(String[] aNames) {
		CommandsHolder ret = new CommandsHolder() ;
		for(String n : aNames) {
			ret.add(loadClass(n));
		}
		return ret ;
	}

	
}
