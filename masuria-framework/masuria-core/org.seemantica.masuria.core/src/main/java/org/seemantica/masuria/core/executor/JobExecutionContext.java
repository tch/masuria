package org.seemantica.masuria.core.executor;

import org.seemantica.masuria.core.database.IPeerDatabase;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.router.IPeerRouter;

public class JobExecutionContext {

	private final IPeerRouter router;
	
	private final IPeerExecutor executor;
	
	private final IPeerDatabase<? extends IElement> database;


	
	
	public JobExecutionContext(final IPeerRouter router,
			                   final IPeerExecutor executor,
			                   final IPeerDatabase<? extends IElement> database) {
		this.router = router;
		this.executor = executor;
		this.database = database;
	}

	public IPeerRouter getMessageRouter() {
		return router;
	}

	public IPeerExecutor getProgramExecutor() {
		return executor;
	}

	public IPeerDatabase<? extends IElement> getDatabase() {
		return database;
	}
	
	
}
