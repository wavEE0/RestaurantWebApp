$(document).ready(() => {

    let url = location.href.replace(/\/$/, "");

    if (location.hash) {

        const tablink = url.split("#");

        $('#waiter-nav a[href="#' + tablink[1] + '"]').tab("show");

        url = location.href.replace(/\/#/, "#");

        history.replaceState(null, null, url);

        setTimeout(() => {

            $(window).scrollTop(0);

        }, 300);

    }



    $('a[data-toggle="tab"]').on("click", function() {

        let updatedUrl;

        const tablink = $(this).attr("href");

        if (tablink == "#ready") {

            updatedUrl = url.split("#")[0];

        } else {

            updatedUrl = url.split("#")[0] + tablink;

        }

        updatedUrl += "/";

        history.replaceState(null, null, updatedUrl);

    });

});
