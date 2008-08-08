package org.jdmp.jgroups;

public class PropsUDP {
	private String bindAddress = null;

	private final String UDP = "UDP(mcast_addr=228.10.10.10;mcast_port=45588;tos=8;ucast_recv_buf_size=20000000;ucast_send_buf_size=640000;mcast_recv_buf_size=25000000;mcast_send_buf_size=640000;loopback=false;discard_incompatible_packets=true;max_bundle_size=64000;max_bundle_timeout=30;use_incoming_packet_handler=true;ip_ttl=2;enable_bundling=true;enable_diagnostics=true;thread_naming_pattern=cl;use_concurrent_stack=true;thread_pool.enabled=true;thread_pool.min_threads=2;thread_pool.max_threads=8;thread_pool.keep_alive_time=5000;thread_pool.queue_enabled=true;thread_pool.queue_max_size=1000;thread_pool.rejection_policy=Run;oob_thread_pool.enabled=true;oob_thread_pool.min_threads=1;oob_thread_pool.max_threads=8;oob_thread_pool.keep_alive_time=5000;oob_thread_pool.queue_enabled=false;oob_thread_pool.queue_max_size=100;oob_thread_pool.rejection_policy=Run)";

	private final String PING = "PING(timeout=2000;num_initial_members=3)";

	private final String MERGE = "MERGE2(max_interval=30000;min_interval=10000)";

	private final String FDSOCK = "FD_SOCK";

	private final String FD = "FD(timeout=10000;max_tries=5;shun=true)";

	private final String VERIFY = "VERIFY_SUSPECT(timeout=1500)";

	private final String BARRIER = "BARRIER";

	private final String NAKACK = "pbcast.NAKACK(use_stats_for_retransmission=false;exponential_backoff=150;use_mcast_xmit=true;gc_lag=0;retransmit_timeout=50,300,600,1200;discard_delivered_msgs=true)";

	private final String UNICAST = "UNICAST(timeout=300,600,1200)";

	private final String STABLE = "pbcast.STABLE(stability_delay=1000;desired_avg_gossip=50000;max_bytes=1000000)";

	private final String VIEWSYNC = "VIEW_SYNC(avg_send_interval=60000)";

	private final String GMS = "pbcast.GMS(print_local_addr=true;join_timeout=3000;shun=false;view_bundling=true)";

	private final String FC = "FC(max_credits=500000;min_threshold=0.20)";

	private final String FRAG2 = "FRAG2(frag_size=60000)";

	private final String STATETRANSFER = "pbcast.STATE_TRANSFER";

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();

		if (bindAddress == null) {
			s.append(UDP + ":");
		} else {
			s.append(UDP.substring(0, UDP.length() - 1));
			s.append(";bind_addr=" + bindAddress);
			s.append("):");
		}

		s.append(PING + ":");
		s.append(MERGE + ":");
		s.append(FDSOCK + ":");
		s.append(FD + ":");
		s.append(VERIFY + ":");
		s.append(BARRIER + ":");
		s.append(NAKACK + ":");
		s.append(UNICAST + ":");
		s.append(STABLE + ":");
		s.append(VIEWSYNC + ":");
		s.append(GMS + ":");
		s.append(FC + ":");
		s.append(FRAG2 + ":");
		s.append(STATETRANSFER);

		return s.toString();
	}

	public void setBindAddress(String bindAddress) {
		this.bindAddress = bindAddress;
	}
}
