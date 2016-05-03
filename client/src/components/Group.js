import React, { Component, PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
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
    const { posts, groupActions } = this.props;

    return (
      <div className="main">
        <Topbar title="Group" />

        <AddGroupPostForm
          currentUser={this.props.currentUser}
          groupId={this.props.groupId}
          groupActions={groupActions}
        />

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

Group.propTypes = {
  posts: ImmutablePropTypes.listOf(
    ImmutablePropTypes.contains({
      id: PropTypes.string.isRequired,
      createdBy: PropTypes.string.isRequired,
      groupId: PropTypes.string.isRequired,
      time: PropTypes.string.isRequired,
      message: PropTypes.string.isRequired,
    })
  ),
  groupActions: PropTypes.object.isRequired,
  groupId: PropTypes.string.isRequired,
  currentUser: ImmutablePropTypes.contains({
    id: PropTypes.string.isRequired,
    username: PropTypes.string.isRequired,
    role: PropTypes.string.isRequired,
    token: PropTypes.string.isRequired,
  }).isRequired,
};
