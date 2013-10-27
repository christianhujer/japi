#!/bin/bash

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

function usage {
    cat 1>&2 <<END
Usage: $0 [OPTION]... JARNAME LINKNAME
Installs a jar as "executable program".

JARNAME:  Path to the jar file that shall be copied.
LINKNAME: Name of the link that shall be used for the executable.

Options:
  -h  Display this help information
END
};

while getopts "h" option
do
    case $option in
        h)
            usage
            exit 0
            ;;
        \?)
            usage
            exit 1
            ;;
    esac
done


if [ $# != 2 ]
then
    echo 1>&2 $0: Wrong number of arguments.
    usage
    exit 1
fi
exit

mkdir -p $HOME/bin/jars/
cp "$1"  $HOME/bin/jars/
rm -f   "$HOME/bin/jars/$2.jar"
ln -s   "$HOME/bin/jars/$(basename $1)" "$HOME/bin/jars/$2.jar"
rm -f   "$HOME/bin/$2"
ln -s   "$HOME/bin/imajar.sh" "$HOME/bin/$2"
