import React, { Component, PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import { Link } from 'react-router';

export default class AddGroupPostForm extends Component {
  constructor(props) {
    super(props);

    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(e) {
    e.preventDefault();
    this.props.groupActions.addGroupPost(
      this.props.currentUser,
      this.props.groupId,
      this.refs.content.value
    );
    this.refs.content.value = '';
  }

  render() {
    const subscribeToGroupUrl = `/subscribe-group/${this.props.groupId}`;
    const deleteGroupUrl = `/delete-group/${this.props.groupId}`;
    return (
      <div>
        <h3>Enter content for new post</h3>
        <form onSubmit={this.handleSubmit}>
          <textarea ref="content"></textarea>
          <button type="submit">Create Post</button>

          <div id="group-options">
            <Link
              to={subscribeToGroupUrl}
              className="subscribe-group-link"
            >
              Subscribe to group
            </Link>

            &nbsp;&nbsp;|&nbsp;&nbsp;

            <Link
              to={deleteGroupUrl}
              className="delete-group-link"
            >
              Delete group
            </Link>
          </div>
        </form>
      </div>
    );
  }
}

AddGroupPostForm.propTypes = {
  groupActions: PropTypes.object.isRequired,
  groupId: PropTypes.string.isRequired,
  currentUser: ImmutablePropTypes.contains({
    id: PropTypes.string.isRequired,
    username: PropTypes.string.isRequired,
    role: PropTypes.string.isRequired,
    token: PropTypes.string.isRequired,
  }).isRequired,
};

