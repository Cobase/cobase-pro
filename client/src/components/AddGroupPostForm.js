import React, { Component, PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';

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
    return (
      <div>
        <h3>Enter required content for new post</h3>
        <form onSubmit={this.handleSubmit}>
          <label>Post content:</label>
          <textarea ref="content"></textarea>
          <button type="submit">Create Post</button>
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

