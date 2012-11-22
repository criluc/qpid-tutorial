#!/usr/bin/python
# -*- coding: utf-8 -*-

#################################################################
# File: activemq-client.py                                      #
# Description: Example using http://code.google.com/p/stomppy/  #
#                                                               #
# Author: Cristian Lucchesi <cristian.lucchesi@iit.cnr.it       #
# Last Modified: 2012-11-22 13:06                               #
#################################################################

import time
import sys

import stomp

BROKER_HOST = "www.devel.iit.cnr.it"
BROKER_PORT = 61613

class MyListener(object):
    def on_error(self, headers, message):
        print 'received an error: %s' % message

    def on_message(self, headers, message):
        print 'received a message: %s' % message

conn = stomp.Connection([(BROKER_HOST, BROKER_PORT)])
conn.add_listener(MyListener())
conn.start()
conn.connect()

#Sottoscrizione della coda
conn.subscribe(destination='/queue/test', ack='auto')

#conn.send(' '.join(sys.argv[1:]), destination='/queue/test')
time.sleep(1000)
conn.disconnect()
