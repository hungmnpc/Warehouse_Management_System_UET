import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from "@mui/material"
import AnnouncementIcon from '@mui/icons-material/Announcement';
import React, { ReactNode } from "react";

interface ConfirmDialogProps {
    open: boolean,
    transition?:  any,
    handleClose: () => void | any,
    title: ReactNode | string | undefined,
    content: ReactNode | string | undefined,
    positiveHandle: () => any,
    negativeHandle: () => any,
    customPositiveButton?: ReactNode,
    customNegativeButton?: ReactNode,
    positiveText: string,
    negativeText: string,
}

export const ConfirmDialog = (props: ConfirmDialogProps) => {

    return (
        <Dialog
                open={props.open}
                TransitionComponent={props.transition}
                keepMounted
                onClose={props.handleClose}
                aria-describedby="alert-dialog-slide-description"
            >
                <DialogTitle>
                    {
                        props.title
                    }
                </DialogTitle>
                <DialogContent>
                    <DialogContentText style={{
                        fontSize: '18px'
                    }} id="alert-dialog-slide-description">
                        {
                            props.content
                        }
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button style={{
                        fontWeight: 700,
                        textTransform: 'none'
                    }} variant="text" color="error" onClick={props.negativeHandle}>{props.negativeText}</Button>
                    <Button style={{
                        fontWeight: 700,
                        textTransform: 'none'
                    }} variant="contained" onClick={props.positiveHandle}>{props.positiveText}</Button>
                </DialogActions>
            </Dialog>
    )
}