<!DOCTYPE html>
<html>
<head>
    <style>
    #queryBlock {
        margin: auto;
        margin-top: 10px;
        width: 400px;
    }

    #updateAllBtn {
        margin-left: 2px;
        color: black;
    }

    .form {
        float: left;
    }

    </style>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div id="queryBlock">
    <g:form class="form" action="download">
        <g:textField name="queryUrl" value="${queryUrl}" autofocus="true"></g:textField>
        <g:submitButton name="Submit" value="Загрузить"></g:submitButton>
    </g:form>
    <g:link action="updateAll"><input id="updateAllBtn" type="button" value="Обновить все"></g:link>
</div>
<a href="#list-page" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                           default="Skip to content&hellip;"/></a>

<div id="list-page" class="content scaffold-list" role="main">
    <h1>Ранее проверенные</h1>

    <div class="mainTable">
        <table>
            <tr>
                <th>Ссылка</th>
                <th>Размер, байт</th>
                <th>Время загрузки, мс.</th>
                <th>Количество запросов, шт</th>
                <th>Последний запрос</th>
                <th></th>
            </tr>

            <g:each var="page" in="${pageList}">
                <tr>
                    <th>${page.url}</th>
                    <th>${page.size}</th>
                    <th>${page.time}</th>
                    <th>${page.count}</th>
                    <th>${page.lastUpdateDate.format("dd.MM.YYYY HH:mm:ss")}</th>
                    <th><g:link action="download" params="[queryUrl: page.url]"><input type="button"
                                                                                       value="Обновить"></g:link></th>
                </tr>
            </g:each>

        </table>
    </div>
</div>
</body>
</html>

