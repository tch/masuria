/**
 * 
 */
package org.seemantica.masuria.core.router.peer.local.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.seemantica.masuria.core.PeerLevelComponentBase;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.partitioner.IPeerPartitioner;
import org.seemantica.masuria.core.program.IMessage;
import org.seemantica.masuria.core.router.IMasterRouter;
import org.seemantica.masuria.core.router.IPeerRouter;


/**
 * @author tch
 *
 * TODO;tch: it ought to depend on partitioner
 */
public class LocalPeerRouter extends PeerLevelComponentBase<IMasterRouter>
                                    implements IPeerRouter {

	private ConcurrentMap<IElementId, ConcurrentLinkedQueue<IMessage>> futureMailboxes = new ConcurrentSkipListMap<IElementId, ConcurrentLinkedQueue<IMessage>>();
	
	private ConcurrentMap<IElementId, ConcurrentLinkedQueue<IMessage>> currentMailboxes = new ConcurrentSkipListMap<IElementId, ConcurrentLinkedQueue<IMessage>>();
	
	private volatile int nOfMessages = 0;
	
	private volatile int nOfMessagesInFuture = 0;
	
	
	public LocalPeerRouter() {

	}
	
	
	@Override
	public void dispatch(final IMessage message) {
		
		IPeerPartitioner partitioner = peerManager.getPartitioner();

		if(partitioner.isOnThisPeerPartition(message.getRecipient())) {
			
			deposit(message);

		} else {
			
			getClusterLevelComponent().dispatch(message);
		}
	}


	@Override
	public void deposit(final IMessage message) {
		

		ConcurrentLinkedQueue<IMessage> mailbox;
		
		//TODO;tch: synchronized block that will, perhaps, need more attention (reading from current and adding to future mailboxes is sperate by the step routine )
		synchronized(this) {
			
			mailbox = futureMailboxes.get(message.getRecipient());

			if(mailbox==null) {
				mailbox = new ConcurrentLinkedQueue<IMessage>();
				futureMailboxes.put(message.getRecipient(), mailbox);
			}
			
			mailbox.add(message);
			++nOfMessagesInFuture;			
		}
		
	}
	
	
	
	@Override
	public Iterable<? extends IMessage> messages(final IElementId recipient) {

		//TODO;tch: synchronisation unnecessary as we only read from current mailboxes
		synchronized (this) {
			
			ConcurrentLinkedQueue<? extends IMessage> mailbox = currentMailboxes.get(recipient);
			
			if(mailbox==null)
				mailbox = new ConcurrentLinkedQueue<IMessage>();
			
			return mailbox;
		}
		
	}


	@Override
	public boolean postStep() { 
		//TODO;tch: synchronized block that will require more attention (is it even necessary??)
		
		synchronized (this) {
			
			currentMailboxes = futureMailboxes;
			nOfMessages = nOfMessagesInFuture;
			futureMailboxes = new ConcurrentSkipListMap<IElementId, ConcurrentLinkedQueue<IMessage>>();
			nOfMessagesInFuture = 0;
		}
		
		return nOfMessages == 0; //vote to finish if there are no messages in any of the mailboxes
	}
	

	
	/* (non-Javadoc)
	 * @see info.chodakowski.masuria.core.execution.IPeerRouter#getRecipients()
	 */
	@Override
	public synchronized Iterable<IElementId> getAllRecipients() {
		
		
		Set<IElementId> recipients = new HashSet<IElementId>(nOfMessages);
		
		Set<IElementId> elements = currentMailboxes.keySet();
		
		for(IElementId e : elements) {
			
			ConcurrentLinkedQueue<IMessage> mailbox = currentMailboxes.get(e); 
			if(mailbox != null && !mailbox.isEmpty()) {
				recipients.add(e);
			}
		}
		
		return recipients;
	}


	@Override
	public int nOfMessages() {
		
		return nOfMessages;
	}


	@Override
	public int nOfMessagesInFuture() {
		
		return nOfMessagesInFuture;
	}

	
	@Override
	public IMasterRouter getClusterLevelComponent() {
		
		return this.peerManager.getClusterManager().getMessageRouter();
	}


}

