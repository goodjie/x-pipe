package com.ctrip.xpipe.redis.meta.server.dcchange;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctrip.xpipe.pool.XpipeNettyClientKeyedObjectPool;
import com.ctrip.xpipe.redis.core.entity.Redis;
import com.ctrip.xpipe.redis.core.entity.RedisMeta;
import com.ctrip.xpipe.redis.core.protocal.cmd.PingCommand;

/**
 * @author wenchao.meng
 *
 * Dec 1, 2016
 */
public class AtLeastOneChecker implements HealthChecker{
	
	private static Logger logger = LoggerFactory.getLogger(AtLeastOneChecker.class); 
	
	private List<RedisMeta> redises;
	
	private XpipeNettyClientKeyedObjectPool pool;
	
	public AtLeastOneChecker(List<RedisMeta> list, XpipeNettyClientKeyedObjectPool pool) {
		this.redises = list;
		this.pool = pool;
	}

	@Override
	public boolean check() {
		
		for(Redis  redis : redises){
			
			try {
				new PingCommand(pool.getKeyPool(new InetSocketAddress(redis.getIp(), redis.getPort()))).execute().get();
				return true;
			} catch (InterruptedException | ExecutionException e) {
				logger.info("[check]", e);
			}
		}
		return false;
	}
}
