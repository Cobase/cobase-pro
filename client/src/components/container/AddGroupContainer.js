import React, { Component, PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import AddGroup from '../AddGroup';
import * as groupActions from '../../actions/GroupActions';

class AddGroupContainer extends Component {
  constructor(props) {
    super(props);

    this.onAddGroup = this.onAddGroup.bind(this);
  }

  onAddGroup(title, tags) {
    const { groupActions, currentUser } = this.props; // eslint-disable-line no-shadow

    groupActions.addGroup(currentUser, title, tags);
  }

  render() {
    return (
      <AddGroup onAddGroup={this.onAddGroup} />
    );
  }
}

export default connect(
  state => ({
    currentUser: state.authentication.currentUser.user,
  }),
  dispatch => ({
    groupActions: bindActionCreators(groupActions, dispatch),
  })
)(AddGroupContainer);

AddGroupContainer.propTypes = {
  groupActions: PropTypes.object.isRequired,
  currentUser: ImmutablePropTypes.recordOf({
    id: PropTypes.string.isRequired,
    username: PropTypes.string.isRequired,
    role: PropTypes.string.isRequired,
    token: PropTypes.string.isRequired,
  }).isRequired,
};
