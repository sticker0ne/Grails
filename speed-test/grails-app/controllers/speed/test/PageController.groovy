package speed.test

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class PageController {

    PageService pageService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond pageService.list(params), model: [pageCount: pageService.count()]
    }

    def download(params) {
        def url = params.queryUrl
        def file = new File('tempPage.html').newOutputStream()
        def startTime = System.currentTimeMillis()
        file << new URL(url).openStream()
        file.close()
        def size = new File('tempPage.html').size()
        def finishTime = System.currentTimeMillis()
        def result = finishTime - startTime
        def p = params.page
        if (p == null)
            p = Page.findByUrl(url)

        if (!p) {
            p = new Page(url, result, size, new Date(), 1)
            p.save()
            pageService.save(p)
        } else {
            p.time = result
            p.size = size
            p.lastUpdateDate = new Date()
            p.count++
            p.save()
            pageService.save(p)
        }

        if (params.all == null)
            redirect(action: "list")

    }

    def updateAll() {
        pageService.list().each {
            download([all: 1, queryUrl: it.url, page: it])
        }
        redirect(action: "list")
    }

    def list() {
        respond pageService.list(sort: "lastUpdateDate", order: "desc")
    }

    def show(Long id) {
        respond pageService.get(id)
    }

    def create() {
        respond new Page(params)
    }

    def save(Page page) {
        if (page == null) {
            notFound()
            return
        }

        try {
            pageService.save(page)
        } catch (ValidationException e) {
            respond page.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'page.label', default: 'Page'), page.id])
                redirect page
            }
            '*' { respond page, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond pageService.get(id)
    }

    def update(Page page) {
        if (page == null) {
            notFound()
            return
        }

        try {
            pageService.save(page)
        } catch (ValidationException e) {
            respond page.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'page.label', default: 'Page'), page.id])
                redirect page
            }
            '*' { respond page, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        pageService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'page.label', default: 'Page'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'page.label', default: 'Page'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
