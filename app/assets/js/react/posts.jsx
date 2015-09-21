var GroupPosts = React.createClass({
    loadPostsFromServer: function() {
        $.ajax({
            url: document.location.origin + this.props.url,
            dataType: 'json',
            cache: false,
            success: function(data) {
                this.setState({data: data});
            }.bind(this),
            error: function(xhr, status, err) {
                $('#group-posts').text("Posts could not be loaded.");
            }.bind(this)
        });
    },
    getInitialState: function() {
        return {data: []};
    },
    componentDidMount: function() {
        this.loadPostsFromServer();
    },
    render: function() {
        return (
            <ul className="timeline">
                <PostList data={this.state.data} />
            </ul>
        );
    }
});

var PostList = React.createClass({
    render: function() {
        var postNodes = this.props.data.map(function(post, index) {
            return (
                <Post createdBy={post.createdBy} createdTimestamp={post.createdTimestamp} content={post.content} id={post.id} key={index}></Post>
            );
        });
        return (
            <div>
                {postNodes}
                <li>
                    <i className="fa fa-clock-o bg-gray"></i>
                </li>
            </div>
        );
    }
});

var Post = React.createClass({
    render: function() {

        var divStyle = {
            textAlign: 'right',
            paddingRight: '7px',
            paddingBottom: '2px'
        };

        return (
            <li>
                <i className="fa fa-comments bg-yellow"></i>
                <div className="timeline-item">
                    <span className="time"><i className="fa fa-clock-o"></i>&nbsp;&nbsp;<span data-livestamp={this.props.createdTimestamp}></span></span>
                    <h3 className="timeline-header">{this.props.createdBy}</h3>
                    <div className="timeline-body wordbreak">
                        {this.props.content.split("\n").map(function(item, index) {
                            return (
                                <div>
                                    <span className="post-content">{item}</span>
                                    <br/>
                                </div>
                            )
                        })}
                    </div>
                    <div style={divStyle}>
                        <a href={'/post/' + this.props.id + '/edit'}><i className="fa fa-edit"></i></a>
                    </div>
                </div>
            </li>
        );
    }
});

React.render(
    <GroupPosts url={'/group/' + $('#group-posts').attr("data-group-id") + '/api/posts'}/>,
    document.getElementById('group-posts')
);