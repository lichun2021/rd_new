package com.hawk.game.module.ann7party384;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;

public abstract class Ann7PartyCrossProcessor {

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ java.lang.annotation.ElementType.TYPE })
	public @interface Declare {
		/**
		 * 协议号
		 */
		public int proto() default 0;
	}
	
	public abstract void process(PartyServiceReq req);
}
