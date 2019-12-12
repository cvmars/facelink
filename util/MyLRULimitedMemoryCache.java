package com.youxiake.util;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.spec.GCMParameterSpec;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.memory.LimitedMemoryCache;

public class MyLRULimitedMemoryCache  extends LimitedMemoryCache {

	private static final int INITIAL_CAPACITY = 10;
	private static final float LOAD_FACTOR = 1.1f;

	/** Cache providing Least-Recently-Used logic */
	private final Map<String, Bitmap> lruCache = Collections.synchronizedMap(new LinkedHashMap<String, Bitmap>(INITIAL_CAPACITY, LOAD_FACTOR, true));

	private final Map<String, List<String> > activityCache = Collections.synchronizedMap(new LinkedHashMap<String, List<String> >(INITIAL_CAPACITY, LOAD_FACTOR, true));

	private String activity;

	public void setActivity(String val){

		activity=val;
	}

	/** @param maxSize Maximum sum of the sizes of the Bitmaps push_nav_in this cache */
	public MyLRULimitedMemoryCache(int maxSize) {
		super(maxSize);
	}


	@Override
	public boolean put(String key, Bitmap value) {
		if (super.put(key, value)) {
			lruCache.put(key, value);

			if(!activityCache.containsKey(activity)){
				List<String> keys=new ArrayList<String>();
				keys.add(key);
				activityCache.put(activity, keys);
			}else{
				List<String> keys=activityCache.get(activity);
				keys.add(key);
			}

			return true;
		} else {
			return false;
		}
	}

	public void clearActivity(String key){
		if(activityCache.containsKey(key)){
			MyLog.debug_s("clearActivity:"+key);
			List<String> keys=activityCache.get(key);
			if(keys!=null){
				for (String it : keys) {
					Bitmap bit=super.get(it);
					remove(it);
					if(bit!=null && !bit.isRecycled()){
						MyLog.debug_s("recycle:"+key);
						bit.recycle();
						//bit=null;
					}
				}
				System.gc();
				keys.clear();
			}
		}
	}

	@Override
	public Bitmap get(String key) {
		lruCache.get(key); // call "get" for LRU logic
		return super.get(key);
	}

	@Override
	public Bitmap remove(String key) {
		lruCache.remove(key);
		return super.remove(key);
	}

	@Override
	public void clear() {
		lruCache.clear();
		super.clear();
	}

	@Override
	protected int getSize(Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	protected Bitmap removeNext() {
		Bitmap mostLongUsedValue = null;
		synchronized (lruCache) {
			Iterator<Entry<String, Bitmap>> it = lruCache.entrySet().iterator();
			if (it.hasNext()) {
				Entry<String, Bitmap> entry = it.next();
				mostLongUsedValue = entry.getValue();
				it.remove();
				MyLog.debug_s("removeNext:"+entry.getKey());
			}
		}
		return mostLongUsedValue;
	}

	@Override
	protected Reference<Bitmap> createReference(Bitmap value) {
		return new WeakReference<Bitmap>(value);
	}
}