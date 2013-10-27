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

branch=$(basename $(pwd))

# Returns the next tag for this branch.
# If no tag are avaialable, $branch.0 is returned.
function getNextTag() {
    version=$( (cd ../../tags ; ls) | grep -P '^'$branch'\.\d+$' | sort -g -t . -k 3 | tail -n 1 )
    if [[ -z $version ]]
    then
        version=$branch.0
    else
        if [[ $version =~ ^([0-9]+)\.([0-9]+)\.([0-9]+)$ ]]
        then
            major=${BASH_REMATCH[1]}
            minor=${BASH_REMATCH[2]}
            rel=${BASH_REMATCH[3]}
        else
            echo Cannot split version $version into major and minor
            exit 1
        fi
        version=$major.$minor.$(( $rel + 1 ))
    fi
    echo $version
}

tag=$(getNextTag)
tagdir=tags/$tag
cd ../..
svn cp branches/$branch $tagdir
cd $tagdir
svn commit -m "Created release tag $tag for branch $branch." ..
ant clean dist
rsync -avPe ssh dist/ christianhujer@frs.sourceforge.net:uploads/
