#!/bin/sh

# Copyright (C) 2009  Christian Hujer.
#
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 2.1 of the License, or (at your option) any later version.
#
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

# WARNING
# This program MUST NOT have any options!
# All arguments MUST BE passed further to the invoked jar.

USERJARS=~/bin/jars/
JARNAME=$(basename $0)
JARPATH="$USERJARS/$JARNAME.jar"

case "$(uname)" in
    CYGWIN*) JARPATH=$(cygpath --windows "$JARPATH") ;;
esac

#java -client -Xshare:on -jar "$JARPATH" "$@"
java -jar "$JARPATH" "$@"
