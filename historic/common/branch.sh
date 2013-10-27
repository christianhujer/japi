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

# Returns the latest branch.
# If no branches are avaialable, 0.0 is returned.
function getPreviousBranch() {
    version=$( (cd ../branches ; ls) | grep -P '^\d+\.\d+$' | sort -g -t . -k 2 | sort -g -t . -k 1 -s | tail -n 1 )
    if [[ -z $version ]]
    then
        version=0.0
    fi
    echo $version
}

# Returns the next minor branch.
# For the first branch, 0.1 is returned.
function getNextMinorBranch() {
    version=$(getPreviousBranch)
    if [[ $version =~ ^([0-9]+)\.([0-9]+)$ ]]
    then
        major=${BASH_REMATCH[1]}
        minor=${BASH_REMATCH[2]}
    else
        echo Cannot split version $version into major and minor
        exit 1
    fi
    minor=$(( $minor + 1 ))
    echo $major.$minor
}

# Returns the next major branch.
# For the first major branch, 1.0 is returned.
function getNextMajorBranch() {
    version=$(getPreviousBranch)
    if [[ $version =~ ^([0-9]+)\.([0-9]+)$ ]]
    then
        major=${BASH_REMATCH[1]}
        minor=${BASH_REMATCH[2]}
    else
        echo Cannot split version $version into major and minor
        exit 1
    fi
    major=$(( $major + 1 ))
    echo $major.0
}

if [[ $1 == '-m' ]]
then
    branch=$(getNextMajorBranch)
else
    branch=$(getNextMinorBranch)
fi
branchdir=branches/$branch
svn up
cd ..
svn cp trunk $branchdir
cd $branchdir
svn propset svn:externals "$(svn propget svn:externals . | sed "s/^\^\/common\/trunk common$/^\/common\/trunk@$(svnversion | sed s/[^0-9]//) common/ " )" .
cd ../..
svn commit -m "Created branch $branch." $branchdir
