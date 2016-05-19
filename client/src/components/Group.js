import React, { Component, PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import List from 'immutable';
import PostsList from './PostsList';
import Topbar from './Topbar';
import EmptyContent from './EmptyContent';
import AddGroupPostForm from './AddGroupPostForm';

export default class Group extends Component {
  constructor(props) {
    super(props);

    props.groupActions.getGroupPosts(props.currentUser, props.groupId);
  }

  componentWillReceiveProps(newProps) {
    const { groupActions, currentUser, groupId } = this.props;

    if (newProps.groupId !== groupId) {
      groupActions.getGroupPosts(currentUser, newProps.groupId);
    }
  }

  render() {
    const { posts, groups, groupId, groupActions } = this.props;
    if (!groups.count()) return null;
    const groupTitle = groups.filter(group => group.id === groupId).first().title;

    return (
      <div className="main">
        <Topbar title={groupTitle} />

        <div className="add-post-container">
          <section className="add-post">
            <AddGroupPostForm
              currentUser={this.props.currentUser}
              groupId={this.props.groupId}
              groupActions={groupActions}
            />
          </section>
        </div>

        {posts.size ?
          <PostsList posts={posts} />
          :
          <EmptyContent
            message="No posts in the current group. Would you like to create one?"
          />
        }
      </div>
    );
  }
}

Group.defaultProps = { posts: List, groups: List };

Group.propTypes = {
  posts: ImmutablePropTypes.list,
  groups: ImmutablePropTypes.list,
  groupActions: PropTypes.object.isRequired,
  groupId: PropTypes.string.isRequired,
  currentUser: ImmutablePropTypes.contains({
    id: PropTypes.string.isRequired,
    username: PropTypes.string.isRequired,
    role: PropTypes.string.isRequired,
    token: PropTypes.string.isRequired,
  }).isRequired,
};
