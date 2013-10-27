<?xml version="1.0" encoding="utf-8"?>
<xsl:transform
    version="1.0"
    xmlns:xml="http://www.w3.org/XML/1998/namespace"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xi="http://www.w3.org/2001/XInclude"
    xmlns="http://www.w3.org/1999/xhtml"
    exclude-result-prefixes="xi xsl"
>
    
    <xsl:output
        doctype-public="-//W3C//DTD XHTML 1.1//EN"
        doctype-system="http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd"
        encoding="utf-8"
        indent="no"
        method="xml"
        omit-xml-declaration="no"
    />

    <xsl:template match="log">
        <html xml:lang="de">
            <head>
                <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
                <meta name="Date" content="$Date: {current-dateTime()}$" />
                <title>JAPI Changelog</title>
                <style type="text/css">
                    .paths, .hide {
                        display:none;
                    }
                    .logTable td {
                        vertical-align:top;
                    }
                    .show, .hide {
                        color:#00f;
                    }
                    td.toggle {
                        text-align:center;
                    }
                    td.revision {
                        text-align:right;
                    }
                    .paths {
                        text-wrap:unrestricted; /* CSS Level 3, CSS3 Text Effects Module, 5. Text Wrapping */
                    }
                </style>
                <script type="text/javascript">
                    function show(revision) {
                        document.getElementById("show" + revision).style.display="none";
                        document.getElementById("hide" + revision).style.display="inline";
                        document.getElementById("path" + revision).style.display="block";
                    }
                    function hide(revision) {
                        document.getElementById("show" + revision).style.display="inline";
                        document.getElementById("hide" + revision).style.display="none";
                        document.getElementById("path" + revision).style.display="none";
                    }
                </script>
            </head>
            <body>
                <table class="logTable" border="border">
                    <thead>
                        <tr>
                            <th />
                            <th>Rev</th>
                            <th>Developer</th>
                            <th>Date</th>
                            <th>Commit Message / Changeset Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:apply-templates select="logentry" />
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="logentry">
        <tr>
            <td class="toggle">
                <span class="show" id="show{@revision}" onclick="javascript:show({@revision});">[+]</span>
                <span class="hide" id="hide{@revision}" onclick="javascript:hide({@revision});">[-]</span>
            </td>
            <td class="revision"><xsl:value-of select="@revision" /></td>
            <td><xsl:value-of select="author" /></td>
            <td><xsl:value-of select="date" /></td>
            <td>
                <xsl:value-of select="msg" />
                <xsl:apply-templates select="paths"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="paths">
        <ul id="path{../@revision}" class="paths">
            <xsl:apply-templates select="path" />
        </ul>
    </xsl:template>

    <xsl:template match="path">
        <li>
            <xsl:value-of select="@action" /><xsl:text> </xsl:text>
            <code><a href="http://svn.sourceforge.net/viewcvs.cgi/japi{.}"><xsl:value-of select="." /></a></code>
        </li>
    </xsl:template>
</xsl:transform>
