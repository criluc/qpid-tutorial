import qpid
import sys
import os
from qpid.util import connect
from qpid.connection import Connection
from qpid.datatypes import Message, RangedSet, uuid4
from qpid.queue import Empty

# additional imports for a given example go here

#----- Functions and Classes ----------------------------

# Any functions and classes needed for a given example 
# go here.

#----- Initialization -----------------------------------

#  Set parameters for login

host="127.0.0.1"
port=5672
user="guest"
password="guest"

# Create a connection and a session. The constructor for a session
# requires a UUID to uniquely identify the session.

socket = connect(host, port)
connection = Connection (sock=socket)
connection.start()
session = connection.session(str(uuid4()))

print connection
#----- Main Body of Program --------------------------------

#   Main body of each example goes here

#----- Cleanup ---------------------------------------------

# Close the session before exiting so there are no open threads.
session.close(timeout=10)
