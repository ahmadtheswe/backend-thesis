package com.justahmed99.authapp.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class DomainResolver {
  public static List<String> resolveDomainsToIps(List<String> domains) {
    List<String> ips = new ArrayList<>();
    for (String domain : domains) {
      try {
        InetAddress[] addresses = InetAddress.getAllByName(domain);
        for (InetAddress address : addresses) {
          ips.add(address.getHostAddress());
        }
      } catch (UnknownHostException e) {
        // Handle exception (e.g., log it)
        System.err.println("Failed to resolve domain: " + domain);
      }
    }
    return ips;
  }
}
