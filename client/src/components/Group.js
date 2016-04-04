import React, { Component } from 'react';
import PostsList from './PostsList';
import Topbar from './Topbar';

export default class Group extends Component {
  constructor(props) {
    super(props);

    props.groupActions.getGroupPosts(props.currentUser, props.groupId);
  }

  componentWillReceiveProps(newProps) {
    const { groupActions, currentUser, groupId } = this.props;

    if (newProps.groupId != groupId) {
      groupActions.getGroupPosts(currentUser, newProps.groupId);
    }
  }

  render() {
    const { posts } = this.props;

    return (
      <div className="main">
        <Topbar title="Group" />
        <PostsList posts={posts} />
      </div>
    );
  }
}
