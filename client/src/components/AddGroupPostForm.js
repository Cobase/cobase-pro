import React, { Component, PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import { Link } from 'react-router';
import Dropzone from 'react-dropzone';

export default class AddGroupPostForm extends Component {
  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
  }
  getInitialState() {
    return {
      files: [],
    };
  }

  onDrop(files) {
    console.log('Adding files...');
    this.setState({
      files,
    });
    console.log(this.state);
  }

  handleSubmit(e) {
    e.preventDefault();
    this.props.groupActions.addGroupPost(
      this.props.currentUser,
      this.props.groupId,
      this.refs.content.value,
      this.state.files
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
          <div id="post-form-inputs">
            <textarea className="form-input content" ref="content"></textarea>

            <Dropzone className="form-input dropzone" onDrop={this.onDrop}>
              <div>Files</div>
            </Dropzone>
          </div>

          <div id="group-actions">
            <button className="submit-post-btn" type="submit">Create Post</button>

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

