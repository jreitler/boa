<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
        xmlns:xi="http://www.w3.org/2001/XInclude"
        language="la_CL"
        font-family="BoschOfficeSans-Regular"
        font-size="7.5pt"
        line-height="1.4em"
        color="#000000">
    <fo:layout-master-set>
		 <fo:simple-page-master master-name="default" page-height="297mm" page-width="210mm">
            <fo:region-body region-name="boa-body" margin-top="50mm" margin-bottom="12mm" margin-left="10.4mm" margin-right="10.4mm"/>
			<fo:region-before region-name="boa-before"/>
        </fo:simple-page-master>
    </fo:layout-master-set>

	<fo:page-sequence master-reference="default" force-page-count="auto" hyphenate="true" hyphenation-character="-" hyphenation-push-character-count="1" hyphenation-remain-character-count="1">
		<fo:static-content flow-name="boa-before">
			<fo:block text-align="center" margin-top="20mm">Songlist Name</fo:block>
		</fo:static-content>
		<fo:flow flow-name="boa-body">
			<fo:block>
			
				<fo:table table-layout="fixed" width="100%" border-collapse="collapse" background-color="#EDEDF0">
					<fo:table-column column-width="15%" />
					<fo:table-column column-width="35%" />
					<fo:table-column column-width="15%" />
					<fo:table-column column-width="35%" />
						<fo:table-body>
								<fo:table-row>
									<fo:table-cell font-size="9pt" line-height="1.6em" padding="2pt 0 0 10.4mm">
										<fo:block>page1</fo:block>
									</fo:table-cell>
									<fo:table-cell font-size="9pt" line-height="1.6em" padding="2pt 10.4mm 0 0" text-align="right">
										<fo:block>title1</fo:block>
									</fo:table-cell>
									<fo:table-cell font-size="9pt" line-height="1.6em" padding="2pt 0 0 10.4mm">
										<fo:block>page3</fo:block>
									</fo:table-cell>
									<fo:table-cell font-size="9pt" line-height="1.6em" padding="2pt 10.4mm 0 0" text-align="right">
										<fo:block>title3</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row background-color="#FFFFFF">
									<fo:table-cell font-size="9pt" line-height="1.6em" padding="2pt 0 0 10.4mm">
										<fo:block>page2</fo:block>
									</fo:table-cell>
									<fo:table-cell font-size="9pt" line-height="1.6em" padding="2pt 10.4mm 0 0" text-align="right">
										<fo:block>title2</fo:block>
									</fo:table-cell>
									<fo:table-cell font-size="9pt" line-height="1.6em" padding="2pt 0 0 10.4mm">
										<fo:block>page4</fo:block>
									</fo:table-cell>
									<fo:table-cell font-size="9pt" line-height="1.6em" padding="2pt 10.4mm 0 0" text-align="right">
										<fo:block>title4</fo:block>
									</fo:table-cell>
								</fo:table-row>
						</fo:table-body>
				</fo:table>
			
			</fo:block>
		</fo:flow>
	</fo:page-sequence>
</fo:root>
