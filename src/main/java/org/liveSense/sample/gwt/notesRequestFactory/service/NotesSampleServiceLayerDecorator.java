package org.liveSense.sample.gwt.notesRequestFactory.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.liveSense.servlet.requestfactory.OsgiServiceLayerDecorator;



public class NotesSampleServiceLayerDecorator extends OsgiServiceLayerDecorator {

	public NotesSampleServiceLayerDecorator(ClassLoader classLoader) {
		super(classLoader);
	}

	public <T extends Object> java.util.Set<javax.validation.ConstraintViolation<T>> validate(T domainObject) {
		return null;
	};
		
	@Override
	  public Object invoke(Method domainMethod, Object... args) {
	    Throwable ex;
	    try {
	      domainMethod.setAccessible(true);
	      if (Modifier.isStatic(domainMethod.getModifiers())) {
	        return domainMethod.invoke(null, args);
	      } else {
	        Object[] realArgs = new Object[args.length - 1];
	        System.arraycopy(args, 1, realArgs, 0, realArgs.length);
	        return domainMethod.invoke(args[0], realArgs);
	      }
	    } catch (IllegalArgumentException e) {
	      ex = e;
	    } catch (IllegalAccessException e) {
	      ex = e;
	    } catch (InvocationTargetException e) {
	      //return report(e);
	    	ex = e.getTargetException();
	    }
	    return die(ex, "Could not invoke method %s", args[0].getClass().getName()+"."+domainMethod.getName());
	  }


}
