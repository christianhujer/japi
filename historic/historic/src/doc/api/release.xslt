<?xml version="1.0" encoding="utf-8"?>
<xsl:transform
    version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:html="http://www.w3.org/1999/xhtml"
    xmlns="http://www.w3.org/1999/xhtml"
    xmlns:saxon="http://saxon.sf.net/"
    exclude-result-prefixes="html saxon xsl"
>
    <xsl:param name="project.version" />

    <xsl:output
        doctype-public="-//W3C//DTD XHTML 1.1//EN"
        doctype-system="http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd"
        encoding="utf-8"
        indent="yes"
        method="xml"
        omit-xml-declaration="no"
        cdata-section-elements="html:script html:style"
        saxon:indent-spaces="4"
    />

    <xsl:template match="html:head/@profile"/>
    <xsl:template match="html:html/@version"/>
    <xsl:template match="html:meta[@scheme='CVS']"/>
    <xsl:template match="html:td/@colspan[.='1']|html:td/@rowspan[.='1']|html:th/@colspan[.='1']|html:th/@rowspan[.='1']"/>
    <xsl:template match="html:a/@shape[.='rect']"/>
    <xsl:template match="html:input/@type[.='text']"/>
    <xsl:template match="html:pre/@xml:space[.='preserve']"/>

    <xsl:template match="html:*">
        <xsl:element
            name="{local-name()}"
            namespace="http://www.w3.org/1999/xhtml"
        >
            <xsl:apply-templates select="@*"/>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="html:ul[@id='apiReleases']">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:if test="not(html:li/html:a[@href=concat($project.version, '/')]) and not(contains($project.version, 'pre'))">
                <li><a href="{$project.version}/">JAPI <xsl:value-of select="$project.version"/> API Documentation</a></li>
            </xsl:if>
            <xsl:apply-templates select="node()"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="@xml:base"/>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

</xsl:transform>
