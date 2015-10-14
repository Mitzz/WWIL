package com.enercon.model.thread.map;

import java.util.concurrent.Callable;

public interface MapValueEvaluatorWorker<Key, Value> extends Callable<Value>{
	public Key getKey();
}
