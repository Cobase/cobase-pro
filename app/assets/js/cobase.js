var React = require('react');
var $ = require('jquery');

var GroupPosts = require('./react/GroupPosts.js');
var TweetBox = require('./react/TweetBox.js');

var autosize = require('./autosize.min.js');

require('./../../../public/plugins/slimScroll/jquery.slimscroll.min.js');
require('./../../../public/plugins/iCheck/icheck.js');
require('./../../../public/plugins/jQueryUI/jquery-ui-1.10.3.js');
require('./../../../public/plugins/jQueryUI/jquery-ui-1.10.3.min.js');
require('./../../../public/bootstrap/js/bootstrap.min.js');

require('./tag-it.js');
require('./bootstrap-tagsinput.js');
require('./app.js');


/* Generate switchable tabs */
$(function() {
    $('ul.tabs').each(function(){
        var $active, $content, $links = $(this).find('a');

        $active = $($links.filter('[href="'+location.hash+'"]')[0] || $links[0]);
        $active.addClass('active');

        $content = $($active[0].hash);
        $links.not($active).each(function () {
          $(this.hash).hide();
        });

        $(this).on('click', 'a', function(e){
          $active.removeClass('active');
          $content.hide();

          $active = $(this);
          $content = $(this.hash);

          $active.addClass('active');
          $content.show();

          e.preventDefault();
        });
    });
});

$(function() {
    autosize($('.autosize'));

    var groupPostsContainer = document.getElementById('group-posts');

    if (groupPostsContainer) {
        React.render(
            <GroupPosts url={'/group/' + $('#group-posts').attr('data-group-id') + '/api/posts'}/>,
            groupPostsContainer
        );
    }

    var twitterContainer = document.getElementById('twitter');

    if (twitterContainer) {
        React.render(
            <TweetBox url={'/group/' + $('#twitter-feed').attr('data-group-id') + '/api/tweets'} />,
            twitterContainer
        );
    }
});

$(function () {
    $('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
    });
});

$(function () {
    $('#tags').tagit({
        preprocessTag: function(tag) {
            if (!tag) { return ''; }

            if (tag.substr(0, 1) == '#') {
                return tag;
            } else if (tag.substr(0, 1) == '@@') {
                return tag;
            } else {
                return '#' + tag;
            }

        }
    });
});